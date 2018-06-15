package core.device;

import java.io.IOException;

/**
 * Created by Alexis Couvreur on 14/06/2018.
 */
public interface IDevice {

    String getIdentifier();

    String read() throws IOException;

    void write(String message) throws IOException;

}
