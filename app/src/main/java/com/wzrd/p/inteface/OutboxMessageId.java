package com.wzrd.p.inteface;

/**
 * Created by lk on 2018/2/11.
 */

public class OutboxMessageId {
    public interface getmessageid {
        void onRefresh(String messageid);
    }
}
