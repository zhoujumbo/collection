package com.jibug.cetty.sample.handler;

import com.jibug.cetty.core.Page;
import com.jibug.cetty.core.handler.HandlerContext;
import com.jibug.cetty.core.handler.ProcessHandlerAdapter;

/**
 * @author heyingcai
 */
public abstract class BasePageHandler extends ProcessHandlerAdapter {

    /**
     * 路由解析
     *
     * @param ctx
     * @param page
     */
    protected abstract void parseRoute(HandlerContext ctx, Page page);


}
