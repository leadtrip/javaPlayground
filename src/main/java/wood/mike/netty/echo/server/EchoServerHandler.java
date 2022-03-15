package wood.mike.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Sharable indicates this handler can be shared by multiple channels
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    // Called for each incoming message
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println( "Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    // Notifies the handler that the last call made to channelRead() was the last message in the current batch
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);      // flushes messages to remote peer and closes channel
    }

    // Called if an exception is thrown during the read operation
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}