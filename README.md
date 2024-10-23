[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/3MmVbb7f)
# Debugging 24/25 Exercise 1

## Instructions:
Run these commands while inside the project's root directory:

```
z3 ./fuzz.txt > res1.txt
java -jar ./target/DnF-01.jar ./fuzz.txt > res2.txt
cmp res1.txt res2.txt
```
