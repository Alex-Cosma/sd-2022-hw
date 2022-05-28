package com.lab4.demo.batch;

import javax.sql.DataSource;

import com.lab4.demo.track.TrackRepository;
import com.lab4.demo.track.model.Track;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Bean
public FlatFileItemReader<Track> reader() {
    return new FlatFileItemReaderBuilder<Track>()
            .name("TrackBatchItemReader")
            .resource(new ClassPathResource("Tracks.csv"))
            .delimited()
            .names("id", "artist", "album", "duration", "explicit_lyrics", "link","preview", "title")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Track>() {{
                setTargetType(Track.class);
            }})
            .build();
}

    @Bean
    public TrackBatchItemProcessor processor() {
        return new TrackBatchItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Track> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Track>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO track (id,artist, album,duration,explicit_lyrics,link,preview,title) VALUES (:id, :preview, :album, :duration, :explicit_lyrics, :link, :artist, :title)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    private final TrackRepository trackRepository;
    @Bean
    public Step step1(JdbcBatchItemWriter<Track> writer) {

       trackRepository.deleteAll();
        return stepBuilderFactory.get("step1")
                .<Track, Track> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }



}