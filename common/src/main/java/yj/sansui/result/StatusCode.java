package yj.sansui.result;

/**
 * StatusCode，获取状态码和状态信息
 *
 * @author sansui
 */
public interface StatusCode {
    /**
     * 获取状态码
     *
     * @return code
     */
    Integer getCode();

    /**
     * 获取状态信息
     *
     * @return message
     */
    String getMessage();
}
