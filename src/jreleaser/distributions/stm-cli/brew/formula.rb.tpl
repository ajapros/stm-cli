# {{jreleaserCreationStamp}}
# Custom stm-cli formula: JReleaser's java-binary template plus the man page and shell completions.
{{#brewRequireRelative}}
require_relative "{{.}}"
{{/brewRequireRelative}}

class {{brewFormulaName}} < Formula
  desc "{{projectDescription}}"
  homepage "{{projectLinkHomepage}}"
  url "{{distributionUrl}}"{{#brewDownloadStrategy}}, :using => {{.}}{{/brewDownloadStrategy}}
  version "{{projectVersion}}"
  sha256 "{{distributionChecksumSha256}}"
  license "{{projectLicense}}"

  {{#brewHasLivecheck}}
  livecheck do
    {{#brewLivecheck}}
    {{.}}
    {{/brewLivecheck}}
  end
  {{/brewHasLivecheck}}
  {{#brewDependencies}}
  depends_on {{.}}
  {{/brewDependencies}}

  def install
    libexec.install Dir["*"]
    bin.install_symlink "#{libexec}/bin/{{distributionExecutableUnix}}" => "{{distributionExecutableName}}"
    man1.install libexec/"man/man1/stm-cli.1"
    bash_completion.install libexec/"completions/stm-cli.bash" => "stm-cli"
    zsh_completion.install libexec/"completions/_stm-cli"
    fish_completion.install libexec/"completions/stm-cli.fish"
  end

  test do
    output = shell_output("#{bin}/{{distributionExecutableName}} --version")
    assert_match "{{projectVersion}}", output
  end
end
