package com.project.librarymanagementsystem.utils;

import com.project.librarymanagementsystem.beans.LendingsBean;
import com.project.librarymanagementsystem.entities.Lending;
import jakarta.el.ValueExpression;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter("LendingConverter")
public class LendingConverter implements Converter<Lending> {

    @Override
    public Lending getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        ValueExpression expression = facesContext
                .getApplication()
                .getExpressionFactory()
                .createValueExpression(facesContext.getELContext(), "${lendingsController}", LendingsBean.class);

        LendingsBean controller = expression.getValue(facesContext.getELContext());
        int id;

        try{
            id = Integer.parseInt(s.substring(0,s.indexOf("|")));
        }
        catch (Exception e){
            return null;
        }

        for (Lending lending : controller.getLendings()) {
            try {
                if (lending.getId() == id) {
                    return lending;
                }
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Lending lending) {
        return lending.toString();
    }
}
