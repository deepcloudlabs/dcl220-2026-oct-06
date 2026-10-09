package com.example.service;

import java.util.List;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public interface Sorter<T> {
	List<T> sort(List<T> list);
}
