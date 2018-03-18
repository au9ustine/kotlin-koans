package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int =
        when {
            this.year != other.year -> this.year - other.year
            this.month != other.month -> this.month - other.month
            else -> this.dayOfMonth - other.dayOfMonth
        }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange =
    when {
        this < other -> DateRange(this, other)
        else -> DateRange(other, this)
    }

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate)

operator fun DateRange.contains(d: MyDate): Boolean = this.start <= d && d <= endInclusive
