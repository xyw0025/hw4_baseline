import javax.swing.JOptionPane;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;

/**
 * Creates a GUI that includes:
* - a text field that allows you to enter number corresponding to the amount of the transaction 
* - a text field that allows you to enter category of the transaction
* - a table that shows all the added transactions
* - a button to add new transactions into the table
* - a button that allows you to enter a filter amount and highlights the transactions that have matching filter amount
* - a button that allows you to enter a filter category and highlights the transactions that have matching filter category
* - a button which allows you to delete the selected transaction from the table
* 
* For the MVC architecture pattern, these are the view and the controller.
 */
public class ExpenseTrackerApp {

  /**
   * Creates a GUI that includes:
   * - a text field that allows you to enter number corresponding to the amount of the transaction 
   * - a text field that allows you to enter category of the transaction
   * - a table that shows all the added transactions
   * - a button to add new transactions into the table
   * - a button that allows you to enter a filter amount and highlights the transactions that have matching filter amount
   * - a button that allows you to enter a filter category and highlights the transactions that have matching filter category
   * - a button which allows you to delete the selected transaction from the table
   * 
   * For the MVC architecture pattern, these are the view and the controller. 
   * @param args
   */
  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);
    

    // Initialize view
    view.setVisible(true);



    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

      // Add action listener to the "Apply Category Filter" button
    view.addApplyCategoryFilterListener(e -> {
      try{
      String categoryFilterInput = view.getCategoryFilterInput();
      CategoryFilter categoryFilter = new CategoryFilter(categoryFilterInput);
      if (categoryFilterInput != null) {
          // controller.applyCategoryFilter(categoryFilterInput);
          controller.setFilter(categoryFilter);
          controller.applyFilter();
      }
     }catch(IllegalArgumentException exception) {
    JOptionPane.showMessageDialog(view, exception.getMessage());
    view.toFront();
   }});


    // Add action listener to the "Apply Amount Filter" button
    view.addApplyAmountFilterListener(e -> {
      try{
      double amountFilterInput = view.getAmountFilterInput();
      AmountFilter amountFilter = new AmountFilter(amountFilterInput);
      if (amountFilterInput != 0.0) {
          controller.setFilter(amountFilter);
          controller.applyFilter();
      }
    }catch(IllegalArgumentException exception) {
    JOptionPane.showMessageDialog(view,exception.getMessage());
    view.toFront();
   }});


    // Add action listener to the "Undo" button
    view.addUndoButtonListener(e -> {
      int selectedRowIndex = view.getSelectedRowIndex();
      boolean undoStatus = controller.undoTransaction(selectedRowIndex);
      if (undoStatus == false) {
        JOptionPane.showMessageDialog(view, "Please select a transaction to undo");
        view.toFront();
      }
    });

  }
}
