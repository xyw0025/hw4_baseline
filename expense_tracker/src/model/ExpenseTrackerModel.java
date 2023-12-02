package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model is applying a Observer pattern
 * This is the has-a relationship with the Observer class  
 */
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 

  private List<ExpenseTrackerModelListener> listeners;
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<ExpenseTrackerModelListener>();
  }
  /**
   * Performs the task of adding a transaction to the list of existing transactions
   * @param t Transaction that is being added
   */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    this.stateChanged();
  }
  /**
   * Function to remove a transaction from the list of transactions
   * @param t - Transaction to be removed
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    this.stateChanged();
  }

  /**
   * Returns the current list of existing transactions
   * @return a list of transactions
   */
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }
  /**
   * Gets a list of all transactions that match the filter indices
   * @param newMatchedFilterIndices contains the list of indices of matched transactions
   */
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      this.stateChanged();
  }

  /**
   * Returns a copy of all the matched filter indices
   * @return Copy of matched indices
   */
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      // TODO
      if (listener == null || containsListener(listener)) {
        return false;  
      }
      listeners.add(listener);
      return true;
  }

  /**
   * Returns the number of listeners present in the list
   * @return total number of listeners
   */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      // TODO
      return listeners.size();
  }

  /**
   * Function to check whether a listener is already present in the list or not
   * @param listener Listener whose presence is being checked
   * @return boolean value indicating the result
   */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      // TODO
      return listeners.contains(listener);
  }

  /**
   * Update the state of the listeners
   */
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      // TODO
      for (ExpenseTrackerModelListener listener: listeners) {
        listener.update(this);
      }
  }
}
