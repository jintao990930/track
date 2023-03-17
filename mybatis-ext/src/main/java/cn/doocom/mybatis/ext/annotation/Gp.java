package cn.doocom.mybatis.ext.annotation;

import cn.doocom.mybatis.ext.enums.Bn;

/**
 * Group
 */
public @interface Gp {

    Bn bind() default Bn.AND;

    String id();

}
