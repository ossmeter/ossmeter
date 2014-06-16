module PHP

extend Extractors;

import lang::php::m3::Core;
import lang::php::m3::FillM3;
import lang::php::ast::AbstractSyntax;
import lang::php::util::Utils;

import IO;

public data Language(str version="")
	= php();

public data AST(loc file = |file:///unknown|)
	= phpAST(Script script);

private bool isPHPFile(loc file)
{
	return file.extension in {"php", "inc"};
}

@extractor{}
public rel[Language, loc, M3] extractM3sPHP(loc project, set[loc] files)
{
	return { <php(), file, createM3forScript(file, getAST(file).script)> | file <- files, isPHPFile(file) };
}

@extractor{}
public rel[Language, loc, AST] extractASTsPHP(loc project, set[loc] files)
{
	return { <php(), file, getAST(file)> | file <- files, isPHPFile(file) };
}

private AST getAST(loc file)
{
	if (!isFile(file))
	{
		return errscript("<file> is not a file");
	}

	return getAST(file, lastModified(file));
} 

@memo
private AST getAST(loc file, datetime lastModified)
{
 	// lastModified is only used to ensure @memo is updated when file is
	return phpAST(loadPHPFile(file));
}
