use crate::prelude::*;

#[test]
pub fn day1_pt1_test() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_1/test_input1.txt")?;

    let (left, right) = input
        .into_iter()
        .map(|mut line| (line.pop().unwrap(), line.pop().unwrap()))
        .unzip::<_, _, Vec<_>, Vec<_>>();
    let left = left.into_iter().sorted();
    let right = right.into_iter().sorted();

    let ans = left.zip(right).map(|(l, r)| l.abs_diff(r)).sum::<u128>();

    assert_eq!(ans, 11);
    Ok(())
}

#[test]
pub fn day1_pt1_real() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_1/real_input1.txt")?;

    let (left, right) = input
        .into_iter()
        .map(|mut line| (line.pop().unwrap(), line.pop().unwrap()))
        .unzip::<_, _, Vec<_>, Vec<_>>();
    let left = left.into_iter().sorted();
    let right = right.into_iter().sorted();

    let ans = left.zip(right).map(|(l, r)| l.abs_diff(r)).sum::<u128>();
    dbg!(ans);

    Ok(())
}

#[test]
pub fn day1_pt2_test() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_1/test_input2.txt")?;

    let (right, left) = input
        .into_iter()
        .map(|mut line| (line.pop().unwrap(), line.pop().unwrap()))
        .unzip::<_, _, Vec<_>, Vec<_>>();

    let freq = right.iter().counts();

    let ans = left
        .into_iter()
        .map(|num| {
            num.checked_mul(*freq.get(&num).unwrap_or(&0) as i128)
                .unwrap()
        })
        .sum::<i128>();
    assert_eq!(ans, 31);
    Ok(())
}

#[test]
pub fn day1_pt2_real() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_1/real_input2.txt")?;

    let (right, left) = input
        .into_iter()
        .map(|mut line| (line.pop().unwrap(), line.pop().unwrap()))
        .unzip::<_, _, Vec<_>, Vec<_>>();

    let freq = right.iter().counts();

    let ans = left
        .into_iter()
        .map(|num| {
            num.checked_mul(*freq.get(&num).unwrap_or(&0) as i128)
                .unwrap()
        })
        .sum::<i128>();
    dbg!(ans);
    Ok(())
}
