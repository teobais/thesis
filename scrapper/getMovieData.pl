#!/usr/bin/perl -w
use strict; 
use XML::Simple;
use Data::Dumper;

my $xml = new XML::Simple;

die "Please make that you the movie title is provided into quotes!\n" if(!@ARGV);
my $movie = shift;
$movie =~ s/\s/+/g;

my $cmd = "curl http://www.omdbapi.com/?t=$movie&y=&plot=short&r=xml";
my $movieData = `$cmd`;
my $data = $xml->XMLin( $movieData );

my $released = escapeSingleQuote($data->{movie}->{released});
my $rating = escapeSingleQuote($data->{movie}->{imdbRating});
my $director = escapeSingleQuote($data->{movie}->{director});
my $genre = escapeSingleQuote($data->{movie}->{genre});
my $writer = escapeSingleQuote($data->{movie}->{writer});
my $runtime = escapeSingleQuote($data->{movie}->{runtime});
my $plot = escapeSingleQuote($data->{movie}->{plot});
my $imdb = escapeSingleQuote($data->{movie}->{imdbID});
my $title = escapeSingleQuote($data->{movie}->{title});
my $votes = escapeSingleQuote($data->{movie}->{imdbVotes});
my $poster = escapeSingleQuote($data->{movie}->{poster});
my $year = escapeSingleQuote($data->{movie}->{year});
my $rated = escapeSingleQuote($data->{movie}->{rated});
my $actors = escapeSingleQuote($data->{movie}->{actors});

my $tstamp = time();

print "INSERT INTO movie VALUES (NULL , '$title', '$year', ";
print "'$rated', '$released', '$genre', '$director', '$writer', '$actors', '$plot', ";
print "'$poster', '$runtime', '$rating', '$votes', '$imdb', '$tstamp');\n";

=pod Replace single quote with two single quotes, 
     in order to be sqlite-syntax compatible.
=cut
sub escapeSingleQuote {
	my $str = shift;
	$str =~ s/'/''/g;
	return $str;
}