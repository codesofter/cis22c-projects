/*
	call MonthVisitor that overrides one method: void visit(Date dt) that writes the toString of the parameter to System.out
*/
/*
    @author Modified by So Choi

    Name of Program:    MonthVisitor
    Description:        Program to "visit" a node for a tree.
    Date:               5/26/17
    OS:                 Mac OS X
    Compiler:           terminal (javac)
*/
class MonthVisitor implements Visitor<Date>{
	@Override
	public void visit(Date dt){
		System.out.println(dt.toString());
	}
}