package de.rieckpil.time;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeUtilTest {

  @Mock private TimeProvider timeProvider;

  @InjectMocks private TimeUtil cut;

  @Test
  @DisplayName("Should throw exception when date is in the future")
  void shouldThrowExceptionWhenDateIsInTheFuture() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateInTheFuture = timeProvider.getCurrentDate().plusDays(1);

    assertThrows(
        IllegalArgumentException.class,
        () -> cut.getDiffBetweenCreationDate(creationDateInTheFuture));
  }

  @Test
  @DisplayName("Should return 'today' when comment was created today")
  void shouldReturnTodayWhenCommentWasCreatedToday() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateToday = timeProvider.getCurrentDate();

    String result = cut.getDiffBetweenCreationDate(creationDateToday);

    assertEquals("today", result);
  }

  @Test
  @DisplayName("Should return 'one day ago' when comment was created 1 day ago")
  void shouldReturnOneDayAgoWhenCommentWasCreatedOneDayAgo() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateADayAgo = timeProvider.getCurrentDate().minusDays(1);

    String result = cut.getDiffBetweenCreationDate(creationDateADayAgo);

    assertEquals("one day ago", result);
  }

  @Test
  @DisplayName("Should return '3 days ago' when comment was created 3 days ago")
  void shouldReturn3DaysAgoWhenCommentWasCreatedThreeDaysAgo() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateADayAgo = timeProvider.getCurrentDate().minusDays(3);

    String result = cut.getDiffBetweenCreationDate(creationDateADayAgo);

    assertEquals("3 days ago", result);
  }

  @Test
  @DisplayName("Should return 'one month ago' when comment was created 1 month ago")
  void shouldReturnOneMonthAgoWhenCommentWasCreatedOneMonthAgo() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateADayAgo = timeProvider.getCurrentDate().minusMonths(1);

    String result = cut.getDiffBetweenCreationDate(creationDateADayAgo);

    assertEquals("one month ago", result);
  }

  @Test
  @DisplayName("Should return '6 months ago' when comment was created 6 months ago")
  void shouldReturn6MonthsAgoWhenCommentWasCreatedSixMonthsAgo() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateADayAgo = timeProvider.getCurrentDate().minusMonths(6);

    String result = cut.getDiffBetweenCreationDate(creationDateADayAgo);

    assertEquals("6 months ago", result);
  }

  @Test
  @DisplayName("Should return 'more than a year ago' when comment was created 365 days ago")
  void shouldReturnMoreThanAYearWhenCommentWasCreatedMoreThan365DaysAgo() throws Exception {

    when(timeProvider.getCurrentDate()).thenReturn(LocalDate.of(2020, 12, 24));
    LocalDate creationDateADayAgo = timeProvider.getCurrentDate().minusYears(1);

    String result = cut.getDiffBetweenCreationDate(creationDateADayAgo);

    assertEquals("more than a year ago", result);
  }
}
