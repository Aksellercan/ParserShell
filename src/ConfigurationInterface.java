
public interface ConfigurationInterface {
    Token[] tokenConfig =
            {
                    new Token("output_debug"),
                    new Token("coloured_output"),
                    new Token("enable_stack_traces"),
                    new Token("verbose_log_file"),
                    new Token("use_formatting"),
                    new Token("quiet"),
                    new Token("log_path"),
                    new Token("name_of_logger")
            };
    void MapKeys(boolean update);
}
