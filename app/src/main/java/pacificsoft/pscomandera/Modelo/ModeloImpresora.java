package pacificsoft.pscomandera.Modelo;

import com.starmicronics.starioextension.StarIoExt;

import pacificsoft.pscomandera.Enum.EnumModelCapability;


public class ModeloImpresora extends EnumModelCapability.ModelInfo {
  public ModeloImpresora(String modelTitle,
                         String[]  modelNameArray,
                         StarIoExt.Emulation emulation,
                         String defaultPortSettings,
                         int       defaultPaperSize,
                         boolean   canSetDrawerOpenStatus,
                         boolean   canPrintTextReceiptSample,
                         boolean   canPrintUtf8EncodedText,
                         boolean   canPrintRasterReceiptSample,
                         boolean   canPrintCjk,
                         boolean   canUseBlackMark,
                         boolean   canUseBlackMarkDetection,
                         boolean   canUsePageMode,
                         boolean   canUseCashDrawer,
                         boolean   canUseBarcodeReader,
                         boolean   canUseCustomerDisplay,
                         boolean   canUseAllReceipt,
                         boolean   canGetProductSerialNumber,
                         int       settableUsbSerialNumberLength,
                         boolean   isUsbSerialNumberEnabledByDefault){
    super(modelTitle,
            modelNameArray,
            emulation,
            defaultPortSettings,
            defaultPaperSize,
            canSetDrawerOpenStatus,
            canPrintTextReceiptSample,
            canPrintUtf8EncodedText,
            canPrintRasterReceiptSample,
            canPrintCjk,
            canUseBlackMark,
            canUseBlackMarkDetection,
            canUsePageMode,
            canUseCashDrawer,
            canUseBarcodeReader,
            canUseCustomerDisplay,
            canUseAllReceipt,
            canGetProductSerialNumber,
            settableUsbSerialNumberLength,
            isUsbSerialNumberEnabledByDefault);

  }

}
