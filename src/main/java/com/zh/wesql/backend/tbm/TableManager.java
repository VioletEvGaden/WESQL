package com.zh.wesql.backend.tbm;

import com.zh.wesql.backend.dm.DataManager;
import com.zh.wesql.backend.utils.Parser;
import com.zh.wesql.backend.vm.VersionManager;
import com.zh.wesql.backend.parser.statement.Begin;
import com.zh.wesql.backend.parser.statement.Create;
import com.zh.wesql.backend.parser.statement.Delete;
import com.zh.wesql.backend.parser.statement.Insert;
import com.zh.wesql.backend.parser.statement.Select;
import com.zh.wesql.backend.parser.statement.Update;

public interface TableManager {
    BeginRes begin(Begin begin);
    byte[] commit(long xid) throws Exception;
    byte[] abort(long xid);

    byte[] show(long xid);
    byte[] create(long xid, Create create) throws Exception;

    byte[] insert(long xid, Insert insert) throws Exception;
    byte[] read(long xid, Select select) throws Exception;
    byte[] update(long xid, Update update) throws Exception;
    byte[] delete(long xid, Delete delete) throws Exception;

    public static TableManager create(String path, VersionManager vm, DataManager dm) {
        Booter booter = Booter.create(path);
        booter.update(Parser.long2Byte(0));
        return new TableManagerImpl(vm, dm, booter);
    }

    public static TableManager open(String path, VersionManager vm, DataManager dm) {
        Booter booter = Booter.open(path);
        return new TableManagerImpl(vm, dm, booter);
    }
}
