package ast;

import java.util.ArrayList;

public class ParamListNode extends ArrayList<ParamNode> {
    public ParamListNode() {
        super();
    }

    public ParamListNode(ParamNode param) {
        super();
        this.add(param);
    }

    public ParamListNode addParam(ParamNode param) {
        this.add(param);
        return this;
    }
}
    