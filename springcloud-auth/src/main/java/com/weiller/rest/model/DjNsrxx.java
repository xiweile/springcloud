/*
* create by mybatis-plus-generator  https://github.com/xiweile
*/
package com.weiller.rest.model;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author weiller
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DjNsrxx implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "djxh", type = IdType.INPUT)
    private String djxh;

    private String gdslxDm;

    private String ssdabh;

    private String nsrsbh;

    private String nsrmc;

    private String kzztdjlxDm;

    private String djzclxDm;

    private String fddbrxm;

    private String fddbrsfzjlxDm;

    private String scjydz;

    private String fddbrsfzjhm;

    private String scjydzxzqhszDm;

    private String nsrztDm;

    private String hyDm;

    private String zcdz;

    private String zcdzxzqhszDm;

    private String jdxzDm;

    private String dwlsgxDm;

    private String gdghlxDm;

    private String djjgDm;

    private Date djrq;

    private String zzjgDm;

    private String kqccsztdjbz;

    private String lrrDm;

    private Date lrrq;

    private String xgrDm;

    private Date xgrq;

    private String sjgsdq;

    private String zgswjDm;

    private String zgswskfjDm;

    private String ssglyDm;

    private String fjmqybz;

    private String swdjblbz;

    private String sjtbSj;

    private String nsrbm;

    private String yxbz;

    private String shxydm;

    private String pgjgDm;

    private Date gszxrq;

    private String nsrztlxDm;


}
