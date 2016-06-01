import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

/**
 * Created by Kimjonghak on 2016. 6. 1..
 */
public class AudioCapture {
    public static void main(String args[]){
        try{
            AudioFormat format;
            format = getFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine line = (TargetDataLine)AudioSystem.getLine(info);
            line.open(format);
            line.start();

            Runnable runner = new Runnable() {
                int bufferSize = (int)format.getSampleRate() * format.getFrameSize();
                byte buffer[] = new byte[bufferSize];

                @Override
                public void run() {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();

                    while(true){
                        int count = line.read(buffer, 0, buffer.length);
                        if(count > 0){
                            out.write(buffer, 0, count);
                            System.out.println(buffer.toString());
                        }
                    }
                }
            };

            runner.run();
        } catch (LineUnavailableException e){
            System.out.println("Error!");
        }
    }


    private static AudioFormat getFormat(){
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

}

