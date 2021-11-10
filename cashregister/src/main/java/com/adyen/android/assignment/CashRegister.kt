package com.adyen.android.assignment

import com.adyen.android.assignment.money.Change

/**
 * The CashRegister class holds the logic for performing transactions.
 *
 * @param change The change that the CashRegister is holding.
 */
class CashRegister(private val change: Change) {
    /**
     * Performs a transaction for a product/products with a certain price and a given amount.
     *
     * @param price The price of the product(s).
     * @param amountPaid The amount paid by the shopper.
     *
     * @return The change for the transaction.
     *
     * @throws TransactionException If the transaction cannot be performed.
     */
    private var amounts = change.getElements().toList().asReversed();
    var count = change.getElements().size

    fun performTransaction(price: Long, amountPaid: Change): Change {
        val amountPaidTotal = amountPaid.total
        val balance = amountPaidTotal - price

        when {
            balance == 0L -> return Change.none()
            balance < 0 -> throw TransactionException("Amount paid is less than price of item.")
            balance > change.total -> throw TransactionException("There's insufficient change in the cash register!")
        }

        var balanceLeft: Long = balance
        val moniesToRemove: MutableList<Long> = arrayListOf()
        for(index in 0 until count) {
            moniesToRemove.add(index, 0)
        }
        var index = -1
        var counter = 0

        while (counter < count) {
            val denominations = change.getElements().toList().reversed()
            val currentDenomination = denominations[counter]

            if (index == counter) {
                var denominationsQty = moniesToRemove[counter]
                if (denominationsQty == 0L) {
                    index = -1
                } else {
                    denominationsQty -= 1
                    balanceLeft += currentDenomination.minorValue * 1
                }
                moniesToRemove[counter] = denominationsQty

            } else {
                var denominationAmount = balanceLeft / currentDenomination.minorValue
                if (denominationAmount > change.getCount(currentDenomination)) {
                    denominationAmount = 0
                }
                if (denominationAmount > 0) {
                    if (index == -1) {
                        index = counter
                    }
                    balanceLeft -= (currentDenomination.minorValue * denominationAmount)
                }
                moniesToRemove[counter] = denominationAmount
            }

            if (counter == denominations.size -1) {
                if (balanceLeft != 0L && index != -1 && index != counter) {
                    counter = index -1
                }
            }
            counter++
        }

        if (moniesToRemove.sum() == 0L) {
            throw TransactionException("There are insufficient bills in register!")
        }
        removeDenominations(moniesToRemove)
        return getChange(amounts, moniesToRemove)
    }

    private fun removeDenominations(denominationsToRemove: List<Long>) {
        if (denominationsToRemove.size != amounts.size) {
            throw TransactionException("Invalid set of denomination quantities to remove.")
        }
        for ((index, value) in denominationsToRemove.withIndex()) {
            val denomination: MonetaryElement = amounts.elementAt(index)
            if (change.getCount(denomination) < value) {
                throw TransactionException("There are not enough \$${denomination.minorValue} bills to remove")
            }
            change.remove(denomination, value.toInt())
        }
    }

    private fun getChange(denominations: List<MonetaryElement>, moniesToRemove: MutableList<Long>): Change{
        val change = Change()
        for (index in denominations.indices) {
            change.add(denominations[index], moniesToRemove[index].toInt())
        }
        return change
    }

    class TransactionException(message: String, cause: Throwable? = null) : Exception(message, cause)
}
