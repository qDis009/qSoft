package kz.qBots.qSoft.service;

import kz.qBots.qSoft.data.entity.Order;
import java.io.IOException;

public interface PdfService {
    void createOrderReport(Order order) throws IOException;
}
