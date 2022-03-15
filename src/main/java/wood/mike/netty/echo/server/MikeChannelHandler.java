package wood.mike.netty.echo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class MikeChannelHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Hi it's Mike");
        ctx.fireChannelRead(msg);
        ReferenceCountUtil.retain(msg);
    }
}
