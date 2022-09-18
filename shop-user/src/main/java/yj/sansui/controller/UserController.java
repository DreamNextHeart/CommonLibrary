package yj.sansui.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import yj.sansui.JwtUtil;
import yj.sansui.annotation.PassVerify;
import yj.sansui.bean.entity.UserEntity;
import yj.sansui.exception.CommonException;
import yj.sansui.exception.ExceptionCode;
import yj.sansui.pack.NotControllerResponse;
import yj.sansui.result.Result;
import yj.sansui.service.impl.UserServiceImpl;
import yj.sansui.version1.RedisUtil;

import javax.annotation.Resource;
import java.util.List;


/**
 * UserController,User的控制层，发起请求的接口
 *
 * @author Sansui
 */
@CrossOrigin
@RestController
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * selectById,根据id获取User实例
     *
     * @param id Integer
     * @return Result
     */
    @GetMapping("/User/selectById")
    @PassVerify
    public Result selectById(Integer id) {
        return new Result(userService.selectById(id));
    }

    /**
     * testNameAndAge name和age参数校验
     *
     * @param userEntity UserEntity
     */
    @GetMapping("/User/testNameAndAge")
    public void testNameAndAge(UserEntity userEntity) {
        if (userEntity.getName() == null) {
            throw new CommonException("名字不允许为空");
        }
        if (userEntity.getAge() < 0) {
            throw new CommonException("年龄不允许为负数");
        }
    }

    /**
     * saveUser,传入UserEntity，保存到数据库中
     *
     * @param userEntity UserEntity
     * @annotation Validated, 参数检验
     */
    @GetMapping("/User/saveUser")
    @PassVerify
    public void saveUser(@Validated UserEntity userEntity) {
        userService.saveUser(userEntity);
    }

    /**
     * selectByIdException，根据id查询UserEntity，返回空指针
     *
     * @param id Integer
     * @return Result
     */
    @GetMapping("/User/selectById/exception")
    @PassVerify
    public Result selectByIdException(Integer id) {
        if (userService.selectById(id).getData() == null) {
            throw new CommonException(ExceptionCode.ID_NULL_EXCEPTION, "id：" + id + "不存在");
        } else {
            return new Result(userService.selectById(id));
        }
    }

    /**
     * getUserById，根据id查询UserEntity
     *
     * @param id Integer
     * @return UserEntity
     */
    @GetMapping("/User/getUserById")
    public UserEntity getUserById(Integer id) {
        return userService.getUserById(id);
    }

    /**
     * health，检测是否健康
     *
     * @return “success”
     * @annotation NotControllerResponse 不封装
     */
    @GetMapping("/User/health/pack")
    @NotControllerResponse
    @PassVerify
    public String health() {
        return "success";
    }

    /**
     * jwtCreate,生成token
     * @param passport String
     * @param password String
     * @return token
     */
    @GetMapping("/User/jwt/create")
    @PassVerify
    public String jwtCreate(String passport,String password){
        return JwtUtil.createToken(passport,password);
    }

    /**
     * jwtVerify，验证token
     * @param token String
     * @return true/false
     */
    @GetMapping("/User/jwt/verify")
    public Boolean jwtVerify(String token){
        return JwtUtil.verifyToken(token);
    }

    /**
     * saveRedis,保存键对值到redis数据库
     * @param key String
     * @param value String
     */
    @GetMapping("/User/save/redisTest")
    @PassVerify
    public void saveRedis(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * saveRedisUtil,通过RedisUtil保存redis键值对
     * @param key String
     * @param value String
     */
    @GetMapping("/User/save/redisUtil")
    @PassVerify
    public void saveRedisUtil(String key,String value){
        RedisUtil.setKeyValue(key,value);
    }

    /**
     * addUserEntity，保存UserEntity并存于缓存中
     * @param userEntity UserEntity
     */
    @GetMapping("/User/redis/add")
    @PassVerify
    public void addUserEntity(UserEntity userEntity){
        userService.saveUserEntity(userEntity);
    }

    /**
     * deleteById，根据id删除User
     * @param id Integer
     */
    @GetMapping("/User/redis/delete")
    @PassVerify
    public void deleteById(Integer id){
        userService.deleteById(id);
    }

    /**
     * updateUserEntity,更新User，带Redis
     * @param userEntity UserEntity
     */
    @GetMapping("/User/redis/update")
    @PassVerify
    public void updateUserEntity(UserEntity userEntity){
        userService.updateUser(userEntity);
    }

    /**
     * selectAllBig，小厂可用，查询所有User，QPS<=1000
     * @return List
     */
    @GetMapping("/User/redis/selectAllSmall")
    @PassVerify
    public List selectAllSmall(){
        return userService.selectAllSmall();
    }

    /**
     * selectAllBig，大厂可用，查询所有User，QPS>1000
     * @return List
     */
    @GetMapping("/User/redis/selectAllBig")
    @PassVerify
    public List selectAllBig(){
        return userService.selectAllBig();
    }


}
