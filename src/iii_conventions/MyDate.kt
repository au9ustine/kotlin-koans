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

data class TimeIntervals(val unit: TimeInterval, val amount: Int)

operator fun TimeInterval.times(amount: Int) = TimeIntervals(this, amount)
operator fun MyDate.plus(delta: TimeInterval) = addTimeIntervals(delta, 1)
operator fun MyDate.plus(delta: TimeIntervals) = addTimeIntervals(delta.unit, delta.amount)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : Iterable<MyDate>,
    ClosedRange<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var current = start
        override fun next(): MyDate {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            val result = current
            current = current.nextDay()
            return result
        }

        override fun hasNext(): Boolean = current <= endInclusive

    }
}

operator fun DateRange.contains(d: MyDate): Boolean = this.start <= d && d <= endInclusive
