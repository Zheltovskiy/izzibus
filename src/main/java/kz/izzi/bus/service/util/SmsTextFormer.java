package kz.izzi.bus.service.util;

/**
 * @author Vladislav Zheltovskiy
 */
public final class SmsTextFormer {
    private static final String IZZI_BUS_TEMPLATE = "Поздравляем, вы выиграли сладкий приз! Найдите" +
        " команду IZZI и получите его! Наш инстаграм: instagram.com/bus.izzi";

    private SmsTextFormer() {
        throw new UnsupportedOperationException("Instantiating is not allowed");
    }

    public static String getWinningText() {
        return IZZI_BUS_TEMPLATE;
    }
}
