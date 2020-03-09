import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
/**
 * 控制树莓派上的 GPIO 实例
 * 让继电器每隔一秒就切换一下状态
 **/
public class Test 
{
    public static void main(String[] args) throws InterruptedException 
    {
        // 创建一个 GPIO 控制器
        final GpioController gpio = GpioFactory.getInstance();
        // 获取 1 号 GPIO 针脚并设置高电平状态，对应的是树莓派上的 12 号针脚。
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED", PinState.HIGH);
        while(true)
        {
			//设置高电平
			pin.high();
			System.out.println("打开继电器");
			//睡眠 1 秒
			Thread.sleep(1000);
			//设置低电平
			pin.low();
			System.out.println("关闭继电器");
			Thread.sleep(1000);
			//切换状态
			//pin.toggle();
		}
    }
}
