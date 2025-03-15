package com.example.formulaapi.baseFiles;

import java.util.List;

public abstract class BaseResponse<T> {

    public abstract List<T> getItems();
}
