package com.thirdparty.modelToObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.askit.exception.ModellToObjectException;
import com.thirdparty.entities.Column;
import com.thirdparty.entities.Entity;

public class ResultSetMapper<T> {

	public List<T> mapResultSetToObject(final ResultSet resultSet, final Class<T> outputClass) throws ModellToObjectException {
		final List<T> outputList = new ArrayList<T>();

		try {
			if (resultSet != null) {
				if (outputClass.isAnnotationPresent(Entity.class)) {
					final ResultSetMetaData metaData = resultSet.getMetaData();
					final Field[] fields = outputClass.getDeclaredFields();

					while (resultSet.next()) {
						final T bean = outputClass.newInstance();

						for (int iterator = 0; iterator < metaData.getColumnCount(); iterator++) {
							final String columnName = metaData.getColumnName(iterator + 1);
							final Object columnValue = resultSet.getObject(iterator + 1);

							for (final Field field : fields) {
								if (field.isAnnotationPresent(Column.class)) {
									final Column column = field.getAnnotation(Column.class);
									if (column.name().equalsIgnoreCase(columnName) && columnValue != null) {
										BeanUtils.setProperty(bean, field.getName(), columnValue);
										break;
									}
								}
							}

						}
						outputList.add(bean);
					}

				} else {
					throw new ModellToObjectException("No annotations found");
				}
			} else {
				return null;
			}
		} catch (final IllegalAccessException | InvocationTargetException | SQLException | InstantiationException e) {
			throw new ModellToObjectException(e.getMessage());
		}
		return outputList;
	}
}