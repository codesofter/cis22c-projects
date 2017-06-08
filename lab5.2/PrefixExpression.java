/*
    Using a LinkedQueue<String> parameter which contains the tokens of a PREFIX Expression (operator left right), write a recursive public static method to evaluate (and return as a double) the expression (or partial expression) using the following pseudocode: (the queue of strings is already filled)

    prefixEvaluate(queue): double
    IF the queue is not empty Then
         Get the next token in the queue (dequeue from our LinkedQueue)
         IF it's an operator Then
               ASSIGN to left, prefixEvaluate(queue)
               ASSIGN to right, prefixEvaluate(queue)
               switch(token.charAt(0))
                    '+': RETURN left+right
                    '-': RETURN left-right
                    '*': RETURN left*right
                    '/': RETURN left/right
               end switch
         ELSE
               ASSIGN to operand, token converted to a double
               RETURN operand
         ENDIF
    ELSE
         RETURN 0
    ENDIF
*/
public class PrefixExpression{
  public static double prefixEvaluate(LinkedQueue<String> queue){
    String nextToken = "";
    String left = "";
    String right = "";

    if(!queue.isEmpty()){
      nextToken = queue.dequeue();
      if(nextToken.isOperator()){
        left = prefixEvaluate(queue);
        right = prefixEvaluate(queue);
        switch(token.charAt(0)){
          case '+':
            return left + right;
          case '-':
            return left - right;
          case '*':
            return left * right;
          case '/':
            return left / right;
        }
      }else{
        Double operand = Double.parseDouble(nextToken);
        if(!Double.isNaN(operand)){
          return operand;
        }else{
          System.out.println("Cannot parse token '" + nextToken + "' to a double!");
        }
      }
    }else{
      return 0;
    }
  }

  /*
    Checks if the given parameter is an operator.
      @param op   Token containing operator
      @return true if the parameter is an operator: "+", "-", "*", or "/"

  */
  private boolean isOperator(String op){
    return (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"));
  }
}