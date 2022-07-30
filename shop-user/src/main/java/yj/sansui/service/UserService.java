package yj.sansui.service;


import com.baomidou.mybatisplus.extension.service.IService;
import yj.sansui.bean.entity.UserEntity;
import yj.sansui.result.Result;

import java.util.List;


/**
 * UserService，定义User业务方法逻辑的方法接口
 * @author Sansui
 */
public interface UserService  extends IService<UserEntity> {
    /**
     * selectById，输入id，查找userEntity
     * @param id Integer
     * @return Result
     */
    Result selectById(Integer id);

    /**
     * saveUser,输入User，保存到数据库中
     * @param userEntity UserEntity
     * @return true/false
     */
    boolean saveUser(UserEntity userEntity);

    /**
     * getUserById,输入id，查找userEntity
     * @param id Integer
     * @return UserEntity
     */
    UserEntity getUserById(Integer id);

    /**
     * saveUserEntity,保存User，带Redis
     * @param userEntity UserEntity
     * @return true/false
     */
    boolean saveUserEntity(UserEntity userEntity);

    /**
     * deleteById,根据id删除User，带Redis
     * @param id Integer
     * @return true/false
     */
    boolean deleteById(Integer id);

    /**
     * updateUser,更新User资料，带Redis
     * @param userEntity UserEntity
     * @return true/false
     */
    boolean updateUser(UserEntity userEntity);

    /**
     * selectAllSmall，查找全部，小厂可用，QPS<=1000
     * @return List
     */
    List selectAllSmall();

    /**
     * selectAllBig，查找全部，大厂可用，QPS>1000
     * @return List
     */
    List selectAllBig();

}
