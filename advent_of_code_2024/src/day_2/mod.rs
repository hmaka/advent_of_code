use crate::prelude::*;

fn day_2(input: Vec<Vec<i128>>) -> Result<u32> {
    let ans = input
        .into_iter()
        .map(|line| {
            let diffs = line
                .into_iter()
                .tuple_windows()
                .map(|(n1, n2)| n2 - n1)
                .collect_vec();
            let non_mono = diffs.iter().all(|n| *n < 0) || diffs.iter().all(|n| *n > 0);
            let acceptable_incre = diffs.iter().all(|n| (0 < n.abs()) && (n.abs() < 4));

            match non_mono && acceptable_incre {
                true => 1,
                false => 0,
            }
        })
        .sum::<u32>();

    Ok(ans)
}

fn is_safe(line: Vec<i128>) -> bool {
    let diffs = line
        .into_iter()
        .tuple_windows()
        .map(|(n1, n2)| n2 - n1)
        .collect_vec();
    let non_mono = diffs.iter().all(|n| *n < 0) || diffs.iter().all(|n| *n > 0);
    let acceptable_incre = diffs.iter().all(|n| (0 < n.abs()) && (n.abs() < 4));

    non_mono && acceptable_incre
}

fn day_2_pt2(input: Vec<Vec<i128>>) -> Result<u32> {
    let ans = input.into_iter().map(|line| {
        let possible = (0..line.len()).map(|i|{
            line[..i].iter().chain(line[i+1..].iter()).cloned().collect_vec()
        }).collect_vec();

        match is_safe(line) || possible.into_iter().any(is_safe){
            true => 1,
            false => 0,
        }
    }).sum::<u32>();

    Ok(ans)
}

#[test]
fn day_2_pt_1_test() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_2/test_input1.txt")?;
    let res = day_2(input)?;

    assert_eq!(res, 2);
    Ok(())
}

#[test]
fn day_2_pt_1_real() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_2/real_input1.txt")?;

    let res = day_2(input)?;
    assert_eq!(res, 524);
    Ok(())
}

#[test]
fn day_2_pt_2_test() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_2/test_input1.txt")?;
    let res = day_2_pt2(input)?;
    assert_eq!(res, 4);
    Ok(())
}
#[test]
fn day_2_pt_2_real() -> Result<()> {
    let input = read_input_lines_to_numbers("src/day_2/real_input1.txt")?;
    let res = day_2_pt2(input)?;
    assert_eq!(res, 569);
    Ok(())
}
