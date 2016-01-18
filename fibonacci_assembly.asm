# Cait Powell

.data
enternum: .asciiz "Please enter the number of the fibonacci term you would like to calculate: "
.text

# ---- SECTION TO PRINT AND INTERACT WITH USERS ----- #

la $a0, enternum		#load address of initial user prompt into $a0		
li $v0, 4			#load system call for printing a string into $v0		
syscall				#make system call

li $v0, 5			#load system call for reading an integer into $v0, so as to get which fibonacci term to calculate
syscall				#make system call

add $a0, $v0, $zero		#move user input into $a0

jal fib 			#call fibonacci function

add $a0,$v0,$zero		#move result of recursive calls into $a0 from $v0
li $v0,1			#load system call for printing an integer into $v0
syscall				#make system call

li $v0,10			#load system call for exiting into $v0
syscall				#make system call

# ---- RECURSIVE FIBONACCI FUNCTION ----- #

fib:
addi $sp,$sp,-12		#make room on the stack
sw $ra,0($sp)			#store program counter in first freed stack location
sw $s0,4($sp)			#store $s0 in second stack location
sw $s1,8($sp)			#store $s1 in third stack location

add $s0,$a0,$zero		#move contents of $a0 (which is user input) into $s0

addi $t1, $zero, 1		#move 1 into $t1
addi $t2, $zero, 2		#move 2 into $t2	
beq $s0, $zero, return0		#if user input is 0, go to return0 label
beq $s0, $t1, return1		#if user input is 1, go to return1 label
beq $s0, $t2, return2		#if user input is 2, go to return2 label

addi $a0, $s0, -1		#decrement argument by one

jal fib				#recursively call fib again with decremented argument, as exit condtions were not met

add $s1, $zero ,$v0     	#move the result of fib(n-1) to $s1

addi $a0, $s0, -2		#decrement original argument by 2

jal fib               		#recursively call fib again, such that $v0 is set to fib(n-2)

add $v0, $v0, $s1      	 	#add $s1 and $v0 and put them in $v0, so that $v0 is now the sum of fib(n-1) and fib(n-2)
	
#----- EXIT FUNCTION, accessed once an exit condition has been met and the appropriate value has been loaded into $v0 ----#

exitfib:
lw $ra,0($sp)       		#restore registers
lw $s0,4($sp)
lw $s1,8($sp)
addi $sp,$sp,12       		#restore stack
jr $ra				#restore program counter

#----- RETURNS FOR EXIT CONDITIOINS -----#

#if return2 was called, load 2 into $v0 and jump to exitfib
return2: 
li $v0, 2
j exitfib

#if return1 was called, load 1 into $v0 and jump to exitfib
return1:
li $v0,1
j exitfib

#if return0 was called, load 0 into $v0 and jump to exitfib
return0 :     
li $v0,0
j exitfib
