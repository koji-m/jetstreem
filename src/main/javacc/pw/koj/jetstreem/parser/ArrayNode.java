package pw.koj.jetstreem.parser;

import pw.koj.jetstreem.compiler.*;
import java.util.*;

public class ArrayNode extends ExprNode {
    protected Location location;
    protected String ns;
    protected List<String> headers;
    protected List<Node> data;


    public ArrayNode() {}

    public ArrayNode(Location loc) {
        super();
        this.location = loc;
        this.ns = null;
        this.headers = null;
        this.data = new ArrayList<Node>();
    }

    public ArrayNode(Location loc, String ns, List<String> headers, List<Node> data) {
        super();
        this.location = loc;
        this.ns = ns;
        this.headers = headers;
        this.data = data;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<Node> getData() {
        return data;
    }

    public void setData(List<Node> data) {
        this.data = data;
    }

    protected void _dump(Dumper d) {
        // TBD
    }

    public Location location() {
        return location;
    }

    public ArrayNode withHeader() {
        for(int i = 0; i < data.size(); i++) {
          Node d = data.get(i);
          if (d instanceof PairNode) {
            if (headers == null) {
              headers = new ArrayList<String>(data.size());
            }
            PairNode pn = (PairNode)d;
            headers.set(i, pn.key());
            data.set(i, pn.value());
          }
        }
        return this;
    }
        
    public void add(Node n) {
        data.add(n);
    }

    public void prepend(Node n) {
        data.add(0, n);
    }

    public Object accept(Visitor visitor) throws CompileError {
        return visitor.visit(this);
    }       
}

