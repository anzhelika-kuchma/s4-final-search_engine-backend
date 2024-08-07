package com.keyin.service;

import com.keyin.model.Keyword;
import com.keyin.repository.KeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KeywordServiceUnitTest {

    KeywordService keywordServiceUnderTest;

    @Mock
    KeywordRepository mockKeywordRepository;

    @BeforeEach
    void setup() {
        this.keywordServiceUnderTest = new KeywordService(this.mockKeywordRepository);
    }

    @Test
    @DisplayName("Should find keyword by name")
    void shouldFindKeywordByName() {
        String keywordExpected = "test";

        when(this.mockKeywordRepository.findDistinctByName(keywordExpected))
                .thenReturn(Optional.of(new Keyword(keywordExpected)));

        Keyword keywordActual =
                this.keywordServiceUnderTest.findKeywordByName(keywordExpected).orElse(null);

        assertNotNull(keywordActual);
        assertEquals(keywordExpected, keywordActual.getName());
    }

    @Test
    @DisplayName("Should return existed keyword without creating a new one")
    void shouldReturnExistedKeyword() {
        String name = "TEsT";
        String nameExpected = name.toLowerCase();

        when(this.mockKeywordRepository.findDistinctByName(nameExpected))
                .thenReturn(Optional.of(new Keyword(nameExpected)));

        Keyword keywordActual = this.keywordServiceUnderTest.createKeyword(name);

        verify(this.mockKeywordRepository, times(0)).save(any(Keyword.class));

        assertEquals(nameExpected, keywordActual.getName());
    }

    @Test
    @DisplayName("Should return newly created keyword from the query")
    void shouldReturnNewlyCreatedKeyword() {
        String name = "TEsT";
        String nameExpected = name.toLowerCase();

        when(this.mockKeywordRepository.findDistinctByName(nameExpected))
                .thenReturn(Optional.empty());

        when(this.mockKeywordRepository.save(any(Keyword.class))).thenReturn(new Keyword(nameExpected));

        Keyword keywordActual = this.keywordServiceUnderTest.createKeyword(name);

        verify(this.mockKeywordRepository, times(1)).findDistinctByName(nameExpected);
        verify(this.mockKeywordRepository, times(1)).save(any(Keyword.class));

        assertEquals(nameExpected, keywordActual.getName());
    }
}