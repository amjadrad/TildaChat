package ir.tildaweb.tildachat.utils;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.tildaweb.tildachat.R;


public class DateUtils {

    private String TAG = this.getClass().getName();


    public interface DateHelperPresenter {
        void onDateSelected(Date date, String datePersian, String dateAD, String justDatePersian, String justDateAd, String justTime, int requestCode);
    }

    private DateHelperPresenter dateHelperPresenter;

    public void setDateHelperPresenter(DateHelperPresenter dateHelperPresenter) {
        this.dateHelperPresenter = dateHelperPresenter;
    }

    public static String getTodayDate() {
        String date = "", time = "";
        PersianCalendar persianCalendar = new PersianCalendar();
        date = persianCalendar.getPersianYear() + "-" + getWithZeroDate(persianCalendar.getPersianMonth()) + "-" + getWithZeroDate(persianCalendar.getPersianDay());
        Date d = new Date();
        time = getWithZeroDate(d.getHours()) + ":" + getWithZeroDate(d.getMinutes());

        return date + " " + time;
    }


    public static String getTodayDateAD() {
        String date = "", time = "";
        Date d = new Date();
        date = d.getYear() + 1900 + "-" + getWithZeroDate(d.getMonth() + 1) + "-" + getWithZeroDate(d.getDate());
        time = getWithZeroDate(d.getHours()) + ":" + getWithZeroDate(d.getMinutes()) + ":" + getWithZeroDate(d.getSeconds());

        return date + " " + time;
    }

    public String getDateADByJalali(String date) {
        Log.d(TAG, "getDateADByJalali: " + date);
        String dateAD = "";
        try {
            int y = Integer.parseInt(date.substring(0, 4));
            int m = Integer.parseInt(date.substring(5, 7)) - 1;
            int d = Integer.parseInt(date.substring(8, 10));

            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setPersianDate(y, m, d);

            Date date1 = persianCalendar.getTime();

            dateAD = (1900 + date1.getYear()) + "-" + getWithZeroDate(date1.getMonth() + 1) + "-" + getWithZeroDate(date1.getDate());

            if (date.length() > 10) {
                dateAD += " " + date.substring(11);
            }

            Log.d(TAG, "getDateADByJalali: " + dateAD);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateAD;

    }

    public String getDateJalaliByAD(String date, boolean withoutSeconds) {
        Log.d(TAG, "getDateJalaliByAD: " + date);
        String dateAD = "";
        try {
            int y = Integer.parseInt(date.substring(0, 4));
            int m = Integer.parseInt(date.substring(5, 7)) - 1;
            int d = Integer.parseInt(date.substring(8, 10));
            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.set(y, m, d);

            dateAD = (persianCalendar.getPersianYear()) + "-" + getWithZeroDate(persianCalendar.getPersianMonth()) + "-" + getWithZeroDate(persianCalendar.getPersianDay());
            if (date.length() > 11) {
                dateAD += " " + date.substring(11, date.length() - 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateAD;

    }


    public String getDateTimeJalali48ByAd(String adDateTime) {
        return String.format("%s %s", getDateJalaliByAD(adDateTime, false).substring(0, 10), DateUtils.getTime48WithZero(adDateTime.substring(11)));
    }


    public static String getWithZeroDate(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return i + "";
        }
    }

    public static String getWithFormatTimeStamp(int y, int m, int d) {
        return (y + 1900) + "-" + getWithZeroDate(m + 1) + "-" + getWithZeroDate(d);
    }

    public static int getHourMinutes(int h, int m) {
        return (h * 60) + m;
    }

    public static int getHourMinutes(String time) {
        try {
            String[] arr = time.split(":");
            int h = Integer.parseInt(arr[0]);
            int m = Integer.parseInt(arr[1]);
            return (h * 60) + m;
        } catch (Exception e) {
            return 0;
        }
    }


    public static String getTimeWithZero(int h, int m) {
        String result = "";
        if (h < 10) {
            result += "0" + h;
        } else {
            result += h;
        }

        result += ":";
        if (m < 10) {
            result += "0" + m;
        } else {
            result += m;
        }

        return result;
    }

    public static String getTime48WithZero(int h, int m) {
        DateObject dateObject = getTime48(h, m);
        String result = "";
        if (dateObject.hour < 10) {
            result += "0" + dateObject.hour;
        } else {
            result += dateObject.hour;
        }
        result += ":";
        result += dateObject.minute;
        return result;
    }

    public static DateObject getTimeSplit(String time) {
        String[] arr = time.split(":");
        DateObject dateObject = new DateUtils().new DateObject();
        if (arr.length > 0)
            dateObject.hour = Integer.parseInt(arr[0]);
        if (arr.length > 1)
            dateObject.minute = Integer.parseInt(arr[1]);
        if (arr.length > 2)
            dateObject.second = Integer.parseInt(arr[2]);
        return dateObject;
    }

    public static String getTime48WithZero(String time) {
        DateObject dateObject = getTimeSplit(time);
        DateObject dateObject48 = getTime48(dateObject.hour, dateObject.minute);
        String result = "";
        if (dateObject48.hour < 10) {
            result += "0" + dateObject48.hour;
        } else {
            result += dateObject48.hour;
        }
        result += ":";
        result += dateObject48.minute;
        return result;
    }

    public static DateObject getTime48(int hour, int minute) {

        int time24 = (hour * 60 * 60) + (minute * 60);
        int time48 = 2 * time24;

        int hours48 = time48 / (60 * 60);
        int minutes48 = (((time48 - (hours48 * 60 * 60)) / 60) / 6) % 10; // every 3 min(24) is 1 min(48) so (3*2)// 2 because of 24=48/2

        DateObject dateObject = new DateUtils().new DateObject();
        dateObject.hour = hours48;
        dateObject.minute = minutes48;

        return dateObject;
    }

    public static DateObject getTime24(int hour48, int minute48) {

        int time24 = (hour48 * 30) + (minute48 * 3);

        int hours24 = time24 / 60;
        int minutes24 = (time24 - (hours24 * 60));
        DateObject dateObject = new DateUtils().new DateObject();
        dateObject.hour = hours24;
        dateObject.minute = minutes24;

        return dateObject;
    }


    public void getDateTimeDialog(final Context context, boolean preventPastDate, boolean preventFutureDate, final int requestCode) {

        final Map<String, String> result = new HashMap<>();
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog(context)
                .setPositiveButtonString("تایید")
                .setNegativeButton("بیخیال")
                .setActionTextColor(context.getResources().getColor(R.color.colorAccent))
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {

                        final String date = persianCalendar.getPersianYear() + "-" + getWithZeroDate(persianCalendar.getPersianMonth()) + "-" + getWithZeroDate(persianCalendar.getPersianDay());
                        final Date dateAD = persianCalendar.getTime();
                        Log.d(TAG, "onDateSelected: " + persianCalendar.getTime().toString());
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String time = getWithZeroDate(i) + ":" + getWithZeroDate(i1) + ":00";
                                result.put("date_persian", date + " " + time);
                                result.put("date_ad", getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " + time);

                                Date dateAdWithoutTimes = (Date) dateAD.clone();
                                dateAdWithoutTimes.setHours(0);
                                dateAdWithoutTimes.setMinutes(0);
                                dateAdWithoutTimes.setSeconds(0);
                                int minutesAdd = (i * 60) + i1;
                                dateAdWithoutTimes.setTime(dateAdWithoutTimes.getTime() + (minutesAdd * 60000));

                                if (preventPastDate) {
                                    if (dateAdWithoutTimes.getTime() < new Date().getTime()) {
                                        Toast.makeText(context, "تاریخ انتخاب شده مجاز نیست.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dateHelperPresenter.onDateSelected(persianCalendar.getTime(), date + " " + time, getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " + time, date, getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()), time, requestCode);
                                    }
                                }
                                if (preventFutureDate) {
                                    if (dateAdWithoutTimes.getTime() > new Date().getTime()) {
                                        Toast.makeText(context, "تاریخ انتخاب شده مجاز نیست.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dateHelperPresenter.onDateSelected(persianCalendar.getTime(), date + " " + time, getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " + time, date, getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()), time, requestCode);
                                    }
                                }
                                if (!preventPastDate && !preventFutureDate) {
                                    dateHelperPresenter.onDateSelected(persianCalendar.getTime(), date + " " + time, getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()) + " " + time, date, getWithFormatTimeStamp(dateAD.getYear(), dateAD.getMonth(), dateAD.getDate()), time, requestCode);
                                }
                            }
                        }, new Date().getHours(), new Date().getMinutes(), true);

                        timePickerDialog.show();
                    }

                    @Override
                    public void onDisimised() {

                    }

                });


        picker2.show();

    }

    public static Integer diffDateDays(String dateAD) {
        Integer days = null;
        try {
            Calendar calendar = Calendar.getInstance();
            int y = Integer.parseInt(dateAD.substring(0, 4));
            int m = Integer.parseInt(dateAD.substring(5, 7)) - 1;
            int d = Integer.parseInt(dateAD.substring(8, 10));
            calendar.set(y, m, d);
            Calendar calendarToday = Calendar.getInstance();
            long timeDef = (8 * 60 * 60 * 1000) - ((60 * 1000) + (50 * 1000));
            long diff = calendarToday.getTimeInMillis() - ((calendar.getTimeInMillis() - timeDef));
            days = Math.round(diff / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public static Integer diffDateDays(String dateADFirst, String dateAdSecond, boolean withTime) {
        Integer days = null;
        try {
            Calendar calendar1 = Calendar.getInstance();
            int y1 = Integer.parseInt(dateADFirst.substring(0, 4));
            int m1 = Integer.parseInt(dateADFirst.substring(5, 7)) - 1;
            int d1 = Integer.parseInt(dateADFirst.substring(8, 10));
            int h1 = 0;
            int min1 = 0;
            if (withTime) {
                h1 = Integer.parseInt(dateADFirst.substring(11, 13));
                min1 = Integer.parseInt(dateADFirst.substring(14, 16));
            }
            calendar1.set(y1, m1, d1, h1, min1);
            Calendar calendar2 = Calendar.getInstance();
            int y2 = Integer.parseInt(dateAdSecond.substring(0, 4));
            int m2 = Integer.parseInt(dateAdSecond.substring(5, 7)) - 1;
            int d2 = Integer.parseInt(dateAdSecond.substring(8, 10));
            int h2 = 0;
            int min2 = 0;
            if (withTime) {
                h2 = Integer.parseInt(dateAdSecond.substring(11, 13));
                min2 = Integer.parseInt(dateAdSecond.substring(14, 16));
            }
            calendar2.set(y2, m2, d2, h2, min2);

            long timeDef = (8 * 60 * 60 * 1000) - ((60 * 1000) + (50 * 1000));
            long diff = calendar2.getTimeInMillis() - ((calendar1.getTimeInMillis() - timeDef));
            days = Math.round(diff / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }


    public static DateClass diffDateDays(int dateFirst, int dateSecond) {
        DateClass dateClass = new DateClass();
        try {

            int diff = dateSecond - dateFirst;
            int days = diff / (60 * 60 * 24);
            int hours = diff - (days * ((60 * 60 * 24)));
            int minutes = (diff / (60));
            int seconds = diff;

            dateClass.setDays(days);
            dateClass.setHours(hours);
            dateClass.setMinutes(minutes);
            dateClass.setSeconds(seconds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateClass;
    }


    public static class DateClass {
        private int days, hours, minutes, seconds;

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }
    }

    public static String getTodayNameAD() {
        Calendar calendar = Calendar.getInstance();
        int x = calendar.getTime().getDay();
        switch (x) {
            case 0: {
                return "sunday";
            }
            case 1: {
                return "monday";
            }
            case 2: {
                return "tuesday";
            }
            case 3: {
                return "wednesday";
            }
            case 4: {
                return "thursday";
            }
            case 5: {
                return "friday";
            }
            case 6: {
                return "saturday";
            }
            default: {
                return "saturday";
            }
        }

    }

    public DateObject getParsedDate(String dateTime) {
        DateObject dateObject = new DateObject();
        dateObject.year = Integer.parseInt(dateTime.substring(0, 4));
        dateObject.month = Integer.parseInt(dateTime.substring(5, 7));
        dateObject.day = Integer.parseInt(dateTime.substring(8, 10));
        if (dateTime.length() > 11) {
            dateObject.hour = Integer.parseInt(dateTime.substring(11, 13));
            dateObject.minute = Integer.parseInt(dateTime.substring(14, 16));
            if (dateTime.length() > 17) {
                dateObject.second = Integer.parseInt(dateTime.substring(17));
            }
        }
        return dateObject;
    }

    public long getParsedDateMillis(String dateTime) {

        DateObject dateObject = getParsedDate(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateObject.year, dateObject.month - 1, dateObject.day, dateObject.hour, dateObject.minute, dateObject.second);

        return calendar.getTimeInMillis();
    }

    public class DateObject {
        public int year;
        public int month;
        public int day;
        public int hour;
        public int minute;
        public int second;

        @Override
        public String toString() {
            return "DateObject{" +
                    "year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    ", hour=" + hour +
                    ", minute=" + minute +
                    ", second=" + second +
                    '}';
        }
    }


    public static String getTodayDateTime(boolean startDay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        today.setTime(today.getTime());
        if (startDay) {
            today.setHours(0);
            today.setMinutes(0);
            today.setSeconds(0);
        }
        return dateFormat.format(today);
    }

    public DateObject getParsedRemindTime(long timeMillis) {

        long sec = 1000;
        long min = 60 * sec;
        long hour = 60 * min;
        long day = 24 * hour;

        long days = timeMillis / day;
        long hours = (timeMillis - (day * days)) / hour;
        long minutes = (timeMillis - ((day * days) + (hour * hours))) / min;
        long seconds = (timeMillis - ((day * days) + (hour * hours) + (min * minutes))) / sec;

        DateObject dateObject = new DateObject();
        dateObject.day = (int) days;
        dateObject.hour = (int) hours;
        dateObject.minute = (int) minutes;
        dateObject.second = (int) seconds;
        return dateObject;
    }


    public DateObject getParsedRemindTime48(long timeMillis) {

//        timeMillis *= 2;
        long sec = 1000;
        long min = 180 * sec;
        long hour = 10 * min;
        long day = 48 * hour;

        long days = timeMillis / day;
        long hours = (timeMillis - (day * days)) / hour;
        long minutes = (timeMillis - ((day * days) + (hour * hours))) / min;

        DateObject dateObject = new DateObject();
        dateObject.day = (int) days;
        dateObject.hour = (int) hours;
        dateObject.minute = (int) minutes;
        return dateObject;
    }


    public String getParsedRemindTimePersianText(DateObject dateObject) {
        StringBuilder stringBuilder = new StringBuilder();
        if (dateObject.day > 0) {
            stringBuilder.append(dateObject.day);
            stringBuilder.append(" روز");
        }
        if (dateObject.hour > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" و ");
            }
            stringBuilder.append(dateObject.hour);
            stringBuilder.append(" ساعت");
        }
        if (dateObject.minute > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" و ");
            }
            stringBuilder.append(dateObject.minute);
            stringBuilder.append(" دقیقه");
        }
        if (dateObject.second > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" و ");
            }
            stringBuilder.append(dateObject.second);
            stringBuilder.append(" ثانیه");
        }

        return stringBuilder.toString();

    }


    public String getParsedRemindTimePersianText48(DateObject dateObject) {
        StringBuilder stringBuilder = new StringBuilder();
        if (dateObject.day > 0) {
            stringBuilder.append(dateObject.day);
            stringBuilder.append(" روز");
        }
        if (dateObject.hour > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" و ");
            }
            stringBuilder.append(dateObject.hour);
            stringBuilder.append(" ساعت");
        }
        if (dateObject.minute > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" و ");
            }
            stringBuilder.append(dateObject.minute);
            stringBuilder.append(" دقیقه");
        }

        return stringBuilder.toString();

    }


    public String getParsedRemindTimeText(DateObject dateObject) {
        StringBuilder stringBuilder = new StringBuilder();
        if (dateObject.day > 0) {
            if (dateObject.day < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(dateObject.day);
            stringBuilder.append(":");
        }
        if (dateObject.hour > 0) {
            if (dateObject.hour < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(dateObject.hour);
            stringBuilder.append(":");
        }
        if (dateObject.minute > 0) {
            if (dateObject.minute < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(dateObject.minute);
            stringBuilder.append(":");
        }
        if (dateObject.second > 0) {
            if (dateObject.second < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(dateObject.second);
        }

        return stringBuilder.toString();

    }

    public String getParsedRemindTimeText48(DateObject dateObject) {
        StringBuilder stringBuilder = new StringBuilder();
        if (dateObject.day > 0) {
            if (dateObject.day < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(dateObject.day);
            stringBuilder.append(":");
        }
        if (dateObject.hour > 0) {
            if (dateObject.hour < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(dateObject.hour);
            stringBuilder.append(":");
        }
        if (dateObject.minute > 0) {
            stringBuilder.append(dateObject.minute);
        }

        return stringBuilder.toString();

    }

    //Clean Code
    public int diffSeconds(String dateTimeStart, String dateTimeEnd) {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        DateObject dateObjectStart = getParsedDate(dateTimeStart);
        DateObject dateObjectEnd = getParsedDate(dateTimeEnd);
        calendarStart.set(dateObjectStart.year, dateObjectStart.month, dateObjectStart.day, dateObjectStart.hour, dateObjectStart.minute, dateObjectStart.second);
        calendarEnd.set(dateObjectEnd.year, dateObjectEnd.month, dateObjectEnd.day, dateObjectEnd.hour, dateObjectEnd.minute, dateObjectEnd.second);
        return (int) ((calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis()) / 1000);
    }

}