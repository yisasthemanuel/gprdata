Hi mates,

these are cup stats after ${editions_no} editions:

[table]
[trow]
	[th]Manager[/th]
	[th]Wins[/th]
	[th]Finals[/th]
	[th]Semis[/th]
	[th]Knockout[/th]
	[th]Participations[/th]	
	[th]Matches won[/th]
	[th]Matches played[/th]
	[th]Points[/th]
[/trow]
<#list stats as stat>
[trow]
	[td]${stat.manager}[/td]
	[td]${stat.winner}[/td]
	[td]${stat.finalist}[/td]
	[td]${stat.semifinalist}[/td]
	[td]${stat.knockout}[/td]
	[td]${stat.participation}[/td]
	[td]${stat.rounds_won}[/td]
	[td]${stat.rounds_played}[/td]
	[td]${stat.points_earned?string["##0.0"]}[/td]
[/trow]
</#list>
[/table]
