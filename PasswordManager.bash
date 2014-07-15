#!/bin/bash
printf "Enter Password Please: "
stty -echo
read pass
printf "\nEnter Website Please: "
stty echo
read site 
printf '\n'
#echo ${pass/ /}${site/ /}
echo ${pass/ /}${site/ /} |openssl sha1 |cut -c 10-19|xclip -selection c  
#echo ${pass/ /}${site/ /} |openssl sha1 |cut -c 10-20  
history -d $((HISTCMD-0))
exit
