package xiaofeng.expr;

/**
 * Created by xiao on 2018/10/26.
 */
public class EvalVisitor extends ExprBaseVisitor<Integer> {

	@Override
	public Integer visitAssign(ExprParser.AssignContext ctx) {
		String id = ctx.ID().getText();
		Integer value = visit(ctx.expr());
		System.out.println("id:" + id + " value:" + value);
//		this.memory.put(id, value);
		return value;

	}

	@Override
	public Integer visitInt(ExprParser.IntContext ctx) {
		return Integer.valueOf(ctx.INT().getText());
	}

/*	@Override
	public Integer visitId(ExprParser.IdContext ctx ) {

	}*/

	@Override
	public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
		Integer left = visit(ctx.expr(0));
		Integer right = visit(ctx.expr(1));

		if (ctx.op.getType() == ExprParser.MUL){
			return left * right;
		}else{
			return left / right;
		}
	}

	@Override
	public Integer visitAddSub(ExprParser.AddSubContext ctx) {
		Integer left = visit(ctx.expr(0));
		Integer right = visit(ctx.expr(1));

		if (ctx.op.getType() == ExprParser.ADD){
			return left + right;
		}else{
			return left - right;
		}
	}
}
