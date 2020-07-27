package im.zhiyi.boot.netty.iot;


import im.zhiyi.boot.netty.iot.coder.DefaultNettyPackageCoder;
import im.zhiyi.boot.netty.iot.protocol.AuthPackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author : zhiyi
 * Date: 2020/3/3
 */
public class NettyIotClientTest {

    public static void main(String[] args)  throws IOException, InterruptedException {
        DefaultNettyPackageCoder coder = new DefaultNettyPackageCoder();

        AuthPackage authPackage = new AuthPackage(1, (short) 100);

        ByteBuf out = Unpooled.buffer();
        coder.encode(null, authPackage, out);

        byte[] data = new byte[out.readableBytes()];
        out.readBytes(data);
        System.out.println(NettyUtil.bytesToStr(data)); // A5 06 01 01 00 00 00 64 00 65

        Socket socket = new Socket("127.0.0.1", 8000);
        OutputStream outputStream = socket.getOutputStream();
        for (int i = 0; i < 10; i++) {
            outputStream.write(data);
            Thread.sleep(1000);
        }
        outputStream.close();
    }
}