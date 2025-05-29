package qrcode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
//@Path("/some-page")
public class QRCodePage {

    //private final Template qrcode;

    QrCodeService qrCodeService;

    /*@Inject
    public QRCodePage(Template qrcode, QrCodeService qrCodeService) {
        this.qrcode = requireNonNull(qrcode, "page is required");
        this.qrCodeService = qrCodeService;
    }*/

    /*@GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@QueryParam("url") String url) {
        return qrcode.data("base64Image", QrCodeService.toBase64Image(qrCodeService.generateQrCode(url), 4, 4, 0x000000, 0xFFFFFF));
    }*/
}
