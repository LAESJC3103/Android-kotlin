package pacificsoft.pscomandera.Impresion.starBluetooth;

import android.content.Context;

import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;

@SuppressWarnings({"UnusedParameters", "UnusedAssignment"})
public class Communication {
    public enum Result {
        Success,
        ErrorUnknown,
        ErrorOpenPort,
        ErrorBeginCheckedBlock,
        ErrorEndCheckedBlock,
        ErrorWritePort,
        ErrorReadPort,
    }

    public static Result sendCommands(byte[] commands, String portName, String portSettings, int timeout, Context context) {
        Result result = Result.ErrorUnknown;

        StarIOPort port = null;

        try {
            result = Result.ErrorOpenPort;

            port = StarIOPort.getPort(portName, portSettings, timeout, context);

//          // When using an USB interface, you may need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);

            StarPrinterStatus status;

            result = Result.ErrorBeginCheckedBlock;

            status = port.beginCheckedBlock();

            if (status.offline) {
                throw new StarIOPortException("A printer is offline");
            }

            result = Result.ErrorWritePort;

            port.writePort(commands, 0, commands.length);

            result = Result.ErrorEndCheckedBlock;

            port.setEndCheckedBlockTimeoutMillis(30000);     // 30000mS!!!

            status = (StarPrinterStatus)port.endCheckedBlock();

            if (status.coverOpen) {
                throw new StarIOPortException("Printer cover is open");
            }
            else if (status.receiptPaperEmpty) {
                throw new StarIOPortException("Receipt paper is empty");
            }
            else if (status.offline) {
                throw new StarIOPortException("Printer is offline");
            }

            result = Result.Success;
        }
        catch (StarIOPortException e) {

            System.out.print(e.getMessage());
            // Nothing
        }
        finally {
            if (port != null) {
                try {
                    StarIOPort.releasePort(port);

                    port = null;
                }
                catch (StarIOPortException e) {
                    // Nothing
                }
            }
        }

        return result;
    }

    public static Result sendCommandsDoNotCheckCondition(byte[] commands, String portName, String portSettings, int timeout, Context context) {
        Result result = Result.ErrorUnknown;

        StarIOPort port = null;

        try {
            result = Result.ErrorOpenPort;

            port = StarIOPort.getPort(portName, portSettings, timeout, context);

//          // When using an USB interface, you may need to send the following data.
//          byte[] dummy = {0x00};
//          port.writePort(dummy, 0, dummy.length);

            StarPrinterStatus status;

            result = Result.ErrorWritePort;
            status = port.retreiveStatus();

            if (status.rawLength == 0) {
                throw new StarIOPortException("A printer is offline");
            }

            result = Result.ErrorWritePort;
            port.writePort(commands, 0, commands.length);

            result = Result.ErrorWritePort;
            status = port.retreiveStatus();

            if (status.rawLength == 0) {
                throw new StarIOPortException("A printer is offline");
            }

            result = Result.Success;
        }
        catch (StarIOPortException e) {
            // Nothing
        }
        finally {
            if (port != null) {
                try {
                    StarIOPort.releasePort(port);

                    port = null;
                }
                catch (StarIOPortException e) {
                    // Nothing
                }
            }
        }

        return result;
    }


}
