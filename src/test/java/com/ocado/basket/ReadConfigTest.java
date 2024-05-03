package com.ocado.basket;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadConfigTest {
    private final ReadConfig readConfig = new ReadConfig();

    @Test
    void fileCannotBeEmpty() {
        //given
        String filePath = "";
        //when
        //then
        assertThrows(RuntimeException.class, () -> readConfig.readConfigFromFile(filePath));
    }

    @Test
    void fileMustBeValidJson() {
        //given
        String filePath = "src/test/resources/file.txt";
        //when
        //then
        assertThrows(RuntimeException.class, () -> readConfig.readConfigFromFile(filePath));
    }

    @Test
    void readConfigMustReturnValueIfFileNotEmpty() {
        //given
        String filePath = "src/test/resources/configCorrect.json";
        //when
        //then
        assertNotNull(readConfig.readConfigFromFile(filePath));
    }

    @Test
    void readConfigMustReturnNullIfEmptyFile() {
        //given
        String filePath = "src/test/resources/configEmpty.json";
        //when
        //then
        assertNull(readConfig.readConfigFromFile(filePath));
    }

}