/*
	called YearVisitor that overrides one method: void visit(Date dt) that writes the Date in the format: year-month-day (use the accessors)
*/
/*
    @author Modified by So Choi

    Name of Program:    YearVisitor
    Description:        Program to "visit" a node for a tree.
    Date:               5/26/17
    OS:                 Mac OS X
    Compiler:           terminal (javac)
*/
class YearVisitor implements Visitor<Date>{
	@Override
    public void visit(Date dt){
		System.out.println(dt.getYear() + "-" + dt.getMonth() + "-" + dt.getDay());
    }
}