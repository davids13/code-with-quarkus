package qrcode;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("qr")
public class QRCodeResource {

    // https://myfear.substack.com/p/how-to-generate-qr-codes-in-quarkus

    QrCodeService qrCodeService;

    @Inject
    public QRCodeResource(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GET
    @Path("download/svg")
    @Produces(MediaType.APPLICATION_SVG_XML)
    public Response getQRCodeSvgDownload(@QueryParam("text") final String text) {
        if (text == null || text.isBlank()) {
            return Response
                    .status(Response.Status.BAD_GATEWAY)
                    .entity("QRCode data cannot be null or empty")
                    .type(MediaType.TEXT_PLAIN)
                    .header("Content-Disposition", "attachment; filename=qrcode.svg")
                    .build();
        }

        String svg = QrCodeService.toSvgString(qrCodeService.generateQrCode(text),
                4, "#FFFFFF", "#000000", true);

        return Response.ok(svg)
                .header("Content-Disposition", "attachment; filename=qr-code.svg")
                .build();
    }
}
