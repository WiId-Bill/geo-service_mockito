package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageSenderImplTest {
    GeoService geoServiceMock;
    LocalizationService localServiceMock;
    MessageSender message;
    Map<String, String> map = new HashMap<>();
    @Test
    void sendRussian() {
        geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp("172.")).thenReturn(new Location(null, Country.RUSSIA, null, 0));

        localServiceMock = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        message = new MessageSenderImpl(geoServiceMock, localServiceMock);
        map.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");

        String actual = message.send(map);
        Assertions.assertEquals("Добро пожаловать", actual);
    }
    @Test
    void sendNotRussian() {
        geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp("96.")).thenReturn(new Location(null, Country.USA, null, 0));

        localServiceMock = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localServiceMock.locale(Country.USA)).thenReturn("Welcome");

        message = new MessageSenderImpl(geoServiceMock, localServiceMock);
        map.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.");

        String actual = message.send(map);
        Assertions.assertEquals("Welcome", actual);
    }
}