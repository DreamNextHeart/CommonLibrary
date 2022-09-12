package yj.sansui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import yj.sansui.RedisConstant;
import yj.sansui.RedisUtil;
import yj.sansui.bean.entity.UserEntity;
import yj.sansui.exception.CommonException;
import yj.sansui.exception.ExceptionCode;
import yj.sansui.mapper.UserMapper;
import yj.sansui.result.Result;
import yj.sansui.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Sansui
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserServiceImpl userService;

    /**
     * selectById，输入id，获取该id的UserEntity
     * @param id Integer
     * @return Result
     */
    @Override
    public Result selectById(Integer id) {
        return new Result(userService.getById(id));
    }

    /**
     * saveUser，保存User
     * @param userEntity UserEntity
     * @return true/false
     */
    @Override
    public boolean saveUser(UserEntity userEntity){
        return userService.save(userEntity);
    }

    /**
     * getUserById,输入id，获取该id的UserEntity
     * @param id Integer
     * @return UserEntity
     */
    @Override
    public UserEntity getUserById(Integer id){
        return userService.getById(id);
    }

    /**
     * saveUserEntity,保存User，带Redis
     * @param userEntity UserEntity
     * @return true/false
     */
    @Override
    public boolean saveUserEntity(UserEntity userEntity){
        if(userService.saveUser(userEntity)){
            String key= RedisConstant.User_Id_Key+userEntity.getId();
            RedisUtil.setKeyValue(key,userEntity);
            return true;
        }else{
            throw new CommonException(ExceptionCode.SAVE_EXCEPTION,"保存id为"+userEntity.getId()+"的User失败");
        }
    }

    /**
     * deleteById，根据id删除User，带Redis
     * @param id Integer
     * @return true/false
     */
    @Override
    public boolean deleteById(Integer id){
        if(userService.removeById(id)){
            String key= RedisConstant.User_Id_Key+id;
            RedisUtil.del(key);
            return true;
        }else {
            throw new CommonException(ExceptionCode.DELETE_EXCEPTION,"删除id为"+id+"的User失败");
        }
    }

    /**
     * updateUser,更新UserEntity，带Redis
     * @param userEntity UserEntity
     * @return true/false
     */
    @Override
    public boolean updateUser(UserEntity userEntity){
        if(userService.updateById(userEntity)){
            String key=RedisConstant.User_Id_Key+userEntity.getId();
            RedisUtil.setKeyValue(key,userEntity);
            return true;
        }else {
            throw new CommonException(ExceptionCode.UPDATE_EXCEPTION,"更新id为"+userEntity.getId()+"的User失败");
        }
    }

    /**
     * selectAllSmall，小厂可用，查询所有User，QPS<=1000
     * @return List
     */
    @Override
    public List selectAllSmall(){
        //先查缓存，缓存没有再查数据库
        List users=(List) RedisUtil.getByKey(RedisConstant.All_User_Key);
        if(users==null){
            users=userService.list();
            if(users==null){
                //数据库也没有则返回异常
                throw new CommonException(ExceptionCode.SELECT_EXCEPTION,"查询失败");
            }else {
                //数据库有则写入缓存再返回
                RedisUtil.setKeyValue(RedisConstant.All_User_Key,users);
                return users;
            }
        }
        return users;
    }

    /**
     * selectAllBig，大厂可用，查询所有User，QPS>1000
     * @return List
     */
    @Override
    public List selectAllBig(){
        //先查缓存，缓存没有再查数据库
        List users=(List) RedisUtil.getByKey(RedisConstant.All_User_Key);
        if(users==null){
            //同步锁，确认没有线程修改
            synchronized (UserMapper.class){
                //二次查询确认
                users=(List) RedisUtil.getByKey(RedisConstant.All_User_Key);
                if(users==null){
                    users=userService.list();
                    if(users==null){
                        throw new CommonException(ExceptionCode.SELECT_EXCEPTION,"查询失败");
                    }else {
                        RedisUtil.setKeyValue(RedisConstant.All_User_Key,users);
                        return users;
                    }
                }
            }
        }
        return users;
    }


}
