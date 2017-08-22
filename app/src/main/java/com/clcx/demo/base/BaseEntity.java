package com.clcx.demo.base;

import java.io.Serializable;

/**
 * Created by ljc123 on 2016/10/21.
 */
public interface BaseEntity {
    class BaseBean implements Serializable {
        public long id;
        public String objectId;
    }
}
