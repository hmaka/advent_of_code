use std::fs;
pub mod day_1;
pub mod day_2;
pub mod prelude;

pub fn read_input_lines(file_path: &str) -> anyhow::Result<Vec<String>> {
    Ok(fs::read_to_string(file_path)?
        .lines()
        .map(String::from)
        .collect())
}

/// Grabs all numbers separated by spaces
pub fn read_input_lines_to_numbers(file_path: &str) -> anyhow::Result<Vec<Vec<i128>>> {
    Ok(fs::read_to_string(file_path)?
        .lines()
        .map(|line| {
            line.split_whitespace()
                .map(String::from)
                .map(|num| num.parse::<i128>())
                .collect()
        })
        .collect::<Result<Vec<Vec<i128>>, _>>()?)
}
