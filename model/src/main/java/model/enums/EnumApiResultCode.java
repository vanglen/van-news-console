package model.enums;

/**
 * Created by Administrator on 2017/8/25.
 */
public enum EnumApiResultCode {
    /**
     * 失败
     */
    FAILED(-1),
    /**
     * 缺省
     */
    DEFAULT(0),
    /**
     * 成功
     */
    SUCCESS(1);

    private int value;

    private EnumApiResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
