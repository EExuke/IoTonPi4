import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
/**
 * 远程控制树莓派上的 GPIO 实例
 * 用树莓派访问设备的状态链接以获得设备的当前状态， 然后根据得到的状态来控制GPIO。 
 * 编写程序每 2 秒获取一次状态， 访问时间间隔不建议设置的太短。
 **/
public class ControlLed 
{
    GpioController gpio;
    GpioPinDigitalOutput pin;
    public static void main(String s[])
    {
        //下面的链接就是在Yeelink的设备的状态
        URLString urlStr = "http://api.yeelink.net/v1.0/device/"这里是你的设备编号"/sensor/"这里是你的传感器编号"/datapoints";
        ControlLed cl = new ControlLed();
        cl.gpio = GpioFactory.getInstance();
        cl.pin = cl.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED", PinState.HIGH);
        boolean current = cl.getStatus(urlStr);
        cl.setLedStatus(current);
        while(true)
        {
            try
            {
                if(cl.getStatus(urlStr) != current)
                {
                    current = cl.getStatus(urlStr);
                    cl.setLedStatus(current);
                }
                Thread.sleep(2000);
            }catch(Exception e){e.printStackTrace();}
        }
    }
    public boolean getStatus(String urlStr)
    {
        URL url;boolean on = false;
        try
        {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)
            url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            StringBuilder tempStr = new StringBuilder();
            while (rd.read() != -1) 
            {
                tempStr.append(rd.readLine());
            }
            //System.out.print("--> 服务器上传感器的信息： ");
            String status = tempStr.substring(tempStr.lastIndexOf(":")+1, tempStr.length()-1);
            //System.out.println(status);
            on = status.equals("1")? true:false;
            //System.out.println(on);
            return on;
        }catch(IOException e){e.printStackTrace();}
        return on;
    }
    public void setLedStatus(boolean sta)
    {
        if(sta)
        {
            pin.low();    //因为我的继电器是低电平有效
            System.out.println("--> 更新 GPIO 的状态: 开");
        }
        else 
        {
            pin.high();
            System.out.println("--> 更新 GPIO 的状态: 关");
        }
    }
}

