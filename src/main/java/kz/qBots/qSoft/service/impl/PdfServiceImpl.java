package kz.qBots.qSoft.service.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import kz.qBots.qSoft.config.constants.DeliveryTypeConstants;
import kz.qBots.qSoft.config.constants.PaymentTypeConstants;
import kz.qBots.qSoft.data.component.OrderComponent;
import kz.qBots.qSoft.data.entity.Cart;
import kz.qBots.qSoft.data.entity.Order;
import kz.qBots.qSoft.data.enums.ClientType;
import kz.qBots.qSoft.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
  private final OrderComponent orderComponent;
  private static final String REGULAR_FONT_FILENAME =
      "./src/main/resources/fonts/arial_regular.ttf";
  private static final String BOLD_FONT_FILENAME = "./src/main/resources/fonts/arial_bold.ttf";
  private static final String REPORT_UPLOAD_PATH = "D:\\qshop\\orders\\reports\\%s.pdf";

  private PdfFont getRegularFont() throws IOException {
    return PdfFontFactory.createFont(REGULAR_FONT_FILENAME, "Identity-H", true);
  }

  private PdfFont getBoldFont() throws IOException {
    return PdfFontFactory.createFont(BOLD_FONT_FILENAME, "Identity-H", true);
  }

  private String getDate(LocalDateTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return time.format(formatter);
  }

  @Override
  public void createOrderReport(Order order) throws IOException {
    String fileName = String.format(REPORT_UPLOAD_PATH, order.getId());
    PdfWriter writer = new PdfWriter(fileName);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);
    PdfFont regular = getRegularFont();
    PdfFont bold = getBoldFont();
    Paragraph paragraph1 = new Paragraph().setTextAlignment(TextAlignment.CENTER);
    paragraph1.add(new Text("Интернет-магазин Ayan Light").setFont(bold).setFontSize(20)).add("\n");
    paragraph1.add(new Text("t.me/ayanmarket_bot").setFont(regular).setFontSize(12)).add("\n");
    paragraph1.add(new Text("Заказ №" + order.getId()).setFont(bold).setFontSize(20));
    Paragraph paragraph2 = new Paragraph().setTextAlignment(TextAlignment.LEFT).setFontSize(12);
    paragraph2
        .add(new Text("Время обработки заказа: ").setFont(regular))
        .add(new Text(getDate(order.getCreated())).setFont(bold))
        .add("\n")
        .add(new Text("Способ оплаты: ").setFont(regular))
        .add(
            new Text(PaymentTypeConstants.PAYMENT_TYPE_STRING_MAP.get(order.getPaymentType()))
                .setFont(bold))
        .add("\n")
        .add(new Text("Способ доставки: ").setFont(regular))
        .add(
            new Text(DeliveryTypeConstants.DELIVERY_TYPE_STRING_MAP.get(order.getDeliveryType()))
                .setFont(bold))
        .add("\n")
        .add(new Text("ФИО: ").setFont(regular))
        .add(new Text(order.getFullName()).setFont(bold))
        .add("\n")
        .add(new Text("Номер телефона: ").setFont(regular))
        .add(new Text(order.getPhoneNumber()).setFont(bold))
        .add("\n")
        .add(new Text("Адрес: ").setFont(regular))
        .add(new Text(order.getAddress()).setFont(bold))
        .add("\n")
        .add(new Text("Название ИП: ").setFont(regular))
        .add(new Text(order.getIEName()).setFont(bold))
        .add("\n")
        .add(new Text("Название магазина: ").setFont(regular))
        .add(new Text(order.getShopName()).setFont(bold))
        .add("\n")
        .add("\n")
        .add(new Text("Заказанный товар").setFontSize(16).setFont(bold));
    Table table = new Table(7).setFont(regular);
    table.addCell("№");
    table.addCell("Артикул   ");
    table.addCell("Наименование    ");
    table.addCell("Единица");
    table.addCell("Количество");
    table.addCell(" Цена, тг. ");
    table.addCell(" Сумма, тг. ");
    Set<Cart> carts = order.getCarts();
    int cnt = 1;
    ClientType clientType = order.getUser().getClientType();
    for (Cart cart : carts) {
      table.addCell(String.valueOf(cnt));
      table.addCell(cart.getItem().getArticle());
      table.addCell(cart.getItem().getNameRu());
      table.addCell("шт.");
      table.addCell(String.valueOf(cart.getItemCount()));
      table.addCell(
          String.valueOf(
              clientType.equals(ClientType.RETAIL)
                  ? (cart.getItem().getRetailPrice() - cart.getItem().getDiscount())
                  : cart.getItem().getWholesalePrice()));
      table.addCell(String.valueOf(cart.getTotalPrice() - cart.getTotalDiscount()));
    }
    Paragraph paragraph3=new Paragraph().setTextAlignment(TextAlignment.RIGHT).setFont(bold).setFontSize(12);
    paragraph3.add(new Text("ИТОГО: ")).add(order.getTotal()-order.getDiscount()+" тг.");
    document.add(paragraph1);
    document.add(paragraph2);
    document.add(table);
    document.add(paragraph3);
    document.close();
    order.setPdfFileUrl(fileName);
    orderComponent.update(order);
  }
}
